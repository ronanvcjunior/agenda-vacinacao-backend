package agendavacinacao

import grails.gorm.PagedResultList
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@ReadOnly
class AgendaController {

    AgendaService agendaService

	static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer page, Integer perPage) {
        Integer pageNumber = page ?: 1
        params.max = perPage

        params.offset = perPage ? (pageNumber - 1) * params.max : null

        PagedResultList results = agendaService.buscarTodasAgendas(params)

        if (!results) {
            render status: NOT_FOUND
            return
        }

        respond results, model: [agendas: results, totalRecords: agendaService.count()], view: "agendas"
    }

    def show(Long id) {
        respond agendaService.buscarAgenda(id)
    }

    @Transactional
    def save(Agenda agenda) {
        try {
            if (!agenda) {
                render status: NOT_FOUND
                return
            }

            List<Agenda> agendas = agendaService.cadastrarAgenda(agenda)
            respond agendas, [status: CREATED, view:"show"]
        } catch (Exception e) {
            respond e
        }
    }

    @Transactional
    def update(Agenda agenda) {
        try {
            if (!agenda) {
                render status: NOT_FOUND
                return
            }

            agendaService.atualizarAgenda(agenda)
            respond agenda, [status: OK, view:"show"]
        } catch (Exception e) {
            respond e
        }
    }

    @Transactional
    def delete(Long id) {
        if (!id || !agendaService.excluirAgenda(id)) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    @Transactional
    def deleteList() {
        JSONArray requestBody = request.JSON as JSONArray

        if (!requestBody) {
            render status: BAD_REQUEST
            return
        }

        if (!agendaService.excluirListaAgendas(requestBody)) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
