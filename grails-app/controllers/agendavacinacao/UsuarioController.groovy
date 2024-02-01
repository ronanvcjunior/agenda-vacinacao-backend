package agendavacinacao

import grails.gorm.PagedResultList
import org.grails.web.json.JSONArray

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class UsuarioController {

    UsuarioService usuarioService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer page, Integer perPage) {
        Integer pageNumber = page ?: 1
        params.max = perPage

        params.offset = perPage ? (pageNumber - 1) * params.max : null

        PagedResultList results = usuarioService.buscarTodosUsuarios(params)

        if (!results) {
            render status: NOT_FOUND
            return
        }

        respond results, model: [usuarios: results, totalRecords: usuarioService.count()], view: "usuarios"
    }

    def show(Long id) {
        respond usuarioService.buscarUsuario(id)
    }

    @Transactional
    def save(Usuario usuario) {
        try {
            if (!usuario) {
                render status: NOT_FOUND
                return
            }

            usuarioService.cadastrarUsuario(usuario)
            respond usuario, [status: CREATED, view:"show"]
        } catch (Exception e) {
            respond e
        }
    }

    @Transactional
    def update(Usuario usuario) {
        try {
            if (!usuario) {
                render status: NOT_FOUND
                return
            }

            usuarioService.atualizarUsuario(usuario)
            respond usuario, [status: OK, view:"show"]
        } catch (Exception e) {
            respond e
        }
    }

    @Transactional
    def delete(Long id) {
        if (!id || !usuarioService.excluirUsuario(id)) {
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

        if (!usuarioService.excluirListaUsuarios(requestBody)) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
