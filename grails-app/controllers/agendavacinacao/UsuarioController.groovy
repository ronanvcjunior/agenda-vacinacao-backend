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
class UsuarioController {

    UsuarioService usuarioService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer page, Integer perPage) {
        Integer pageNumber = page ?: 1
        params.max = perPage ?: 10

        params.offset = (pageNumber - 1) * params.max

        PagedResultList results = usuarioService.buscarTodosUsuarios(params)

        respond results, model: [usuarioCount: results.size()]
    }


    def show(Long id) {
        respond usuarioService.buscarUsuario(id)
    }

    @Transactional
    def save(Usuario usuario) {
        try {
            if (usuario == null) {
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
            if (usuario == null) {
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
}
