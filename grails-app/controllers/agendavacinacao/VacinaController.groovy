package agendavacinacao

import grails.gorm.PagedResultList
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class VacinaController {

    VacinaService vacinaService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer page, Integer perPage) {
        Integer pageNumber = page ?: 1
        params.max = perPage

        params.offset = perPage ? (pageNumber - 1) * params.max : null

        PagedResultList results = vacinaService.buscarTodasVacinas(params)

        if (!results) {
            render status: NOT_FOUND
            return
        }

        respond results, model:[vacinaCount: results.size()]
    }

    def show(Long id) {
        respond vacinaService.buscarVacina(id)
    }

    @Transactional
    def save(Vacina vacina) {
        try {
            if (!vacina) {
                render status: NOT_FOUND
                return
            }

            vacinaService.cadastrarVacina(vacina)

            respond vacina, [status: CREATED, view:"show"]
        } catch (ValidationException e) {
            respond vacina.errors
        }
    }

    @Transactional
    def update(Vacina vacina) {
        try {
            if (!vacina) {
                render status: NOT_FOUND
                return
            }

            vacinaService.atualizarVacina(vacina)

            respond vacina, [status: OK, view:"show"]
        } catch (ValidationException e) {
            respond e
        }
    }

    @Transactional
    def delete(Long id) {
        if (!id || !vacinaService.excluirVacina(id)) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
