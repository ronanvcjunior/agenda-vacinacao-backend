package agendavacinacao

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.grails.web.json.JSONArray
import org.hibernate.service.spi.ServiceException

@Transactional
class AlergiaService {

    Alergia cadastrarAlergia(Alergia alergia) {
        try {
            alergia.validate()
            if (alergia.hasErrors()) {
                throw new ValidationException("Erro de validação ao cadastrar alergia.", alergia.errors)
            }

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

    List<Alergia> excluirListaAlergias(JSONArray requestBody) {
        List<Alergia> alergias = requestBody.collect { this.buscarAlergia(it["id"] as Long) }
        try {
            alergias.each {
                it.delete(flush: true)
            }

            return alergias
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir alergia.", e)
        }
    }

    List<Alergia> buscarTodasAlergias(Map<String, Object> params) {
        try {
            return Alergia.findAll(params)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar todas alergias.", e)
        }
    }
}
