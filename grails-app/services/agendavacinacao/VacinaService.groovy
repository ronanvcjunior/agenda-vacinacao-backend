package agendavacinacao

import enums.PeriodicidadeEnum
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.hibernate.service.spi.ServiceException

@Transactional
class VacinaService {

    Vacina cadastrarVacina(Vacina vacina) {
        try {
            vacina.validate()
            if (vacina.hasErrors()) {
                throw new ValidationException("Erro de validação ao cadastrar vacina.", vacina.errors)
            }

            vacina.save(flush: true)

            return vacina
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao salvar vacina.", e)
        }
    }

    Vacina buscarVacina(Long id) {
        try {
            return Vacina.get(id)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar vacina.", e)
        }
    }

    Vacina atualizarVacina(Vacina vacina) {
        try {
            vacina.validate()
            if (vacina.hasErrors()) {
                throw new ValidationException("Erro de validação ao atualizar vacina.", vacina.errors)
            }

            vacina.save(flush: true, failOnError: true)

            return vacina
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao atualizar vacina.", e)
        }
    }

    Vacina excluirVacina(Long id) {
        try {
            Vacina vacina = Vacina.get(id)
            if (vacina) {
                vacina.delete(flush: true)
            }

            return vacina
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir vacina.", e)
        }
    }

    List<Vacina> buscarTodasVacinas(Map<String, Object> params) {
        try {
            return Vacina.findAll(params)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar todas vacinas.", e)
        }
    }

}
