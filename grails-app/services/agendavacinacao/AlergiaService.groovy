package agendavacinacao

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.hibernate.service.spi.ServiceException
import utils.StringFormatter

@Transactional
class AlergiaService {

    Alergia cadastrarAlergia(Alergia alergia) {
        try {
            alergia.validate()
            if (alergia.hasErrors()) {
                throw new ValidationException("Erro de validação ao cadastrar alergia.", alergia.errors)
            }

            alergia.nome = StringFormatter.capitalizarTodaString(alergia.nome)

            alergia.save(flush: true)

            return alergia
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao salvar alergia.", e)
        }
    }

    Alergia buscarAlergia(Long id) {
        try {
            return Alergia.get(id)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar alergia.", e)
        }
    }

    Alergia atualizarAlergia(Alergia alergia) {
        try {
            alergia.validate()
            if (alergia.hasErrors()) {
                throw new ValidationException("Erro de validação ao atualizar alergia.", alergia.errors)
            }

            alergia.nome = StringFormatter.capitalizarTodaString(alergia.nome)

            alergia.save(flush: true, failOnError: true)

            return alergia
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao atualizar alergia.", e)
        }
    }

    Alergia excluirAlergia(Long id) {
        try {
            Alergia alergia = Alergia.get(id)
            if (alergia) {
                alergia.delete(flush: true)
            }

            return alergia
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir alergia.", e)
        }
    }

    List<Alergia> buscarTodasAlergias(Map<String, Object> params) {
        try {
            List<Alergia> result = Alergia.createCriteria().list(params) {
                maxResults(params.max)
                firstResult(params.offset)
            }

            return result
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar todas alergias.", e)
        }
    }
}
