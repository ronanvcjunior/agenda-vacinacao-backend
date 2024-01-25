package agendavacinacao

import grails.gorm.PagedResultList
import grails.validation.ValidationException
import utils.StringFormatter

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class AlergiaController {

    AlergiaService alergiaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer page, Integer perPage) {
        Integer pageNumber = page ?: 1
        params.max = perPage ?: 10

        params.offset = (pageNumber - 1) * params.max

        PagedResultList results = alergiaService.buscarTodasAlergias(params)

        respond results, model: [alergiaCount: results.size()]
    }

    def show(Long id) {
        respond alergiaService.buscarAlergia(id)
    }

    @Transactional
    def save(Alergia alergia) {
        try {
            if (alergia == null) {
                render status: NOT_FOUND
                return
            }

            alergiaService.cadastrarAlergia(alergia)
            respond alergia, [status: CREATED, view:"show"]
        } catch (Exception e) {
            respond e
        }
    }

    @Transactional
    def update(Alergia alergia) {
        try {
            if (alergia == null) {
                render status: NOT_FOUND
                return
            }

            alergiaService.atualizarAlergia(alergia)
            respond alergia, [status: OK, view:"show"]
        } catch (Exception e) {
            respond e
        }
    }

    @Transactional
    def delete(Long id) {
        if (!id || !alergiaService.excluirAlergia(id)) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
