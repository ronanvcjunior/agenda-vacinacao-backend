package agendavacinacao

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.grails.web.json.JSONArray
import org.hibernate.service.spi.ServiceException

@Transactional
class UsuarioService {

    Integer count() {
        return Usuario.count()
    }

    Usuario cadastrarUsuario(Usuario usuario) {
        try {
            usuario.validate()
            if (usuario.hasErrors()) {
                throw new ValidationException("Erro de validação ao cadastrar usuário.", usuario.errors)
            }

            usuario.save(flush: true)


            return usuario
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao salvar usuário.", e)
        }
    }

    Usuario buscarUsuario(Long id) {
        try {
            return Usuario.get(id)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar usuário.", e)
        }
    }

    Usuario atualizarUsuario(Usuario usuario) {
        try {
            usuario.validate()
            if (usuario.hasErrors()) {
                throw new ValidationException("Erro de validação ao atualizar usuário.", usuario.errors)
            }

            usuario.save(flush: true, failOnError: true)


            return usuario
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao atualizar usuário.", e)
        }
    }

    Usuario excluirUsuario(Long id) {
        try {
            Usuario usuario = Usuario.get(id)
            if (usuario) {
                usuario.delete(flush: true)
            }

            return usuario
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir usuário.", e)
        }
    }

    List<Usuario> excluirListaUsuarios(JSONArray requestBody) {
        List<Usuario> usuarios = requestBody.collect { this.buscarUsuario(it["id"] as Long) }
        try {
            usuarios.each {
                it.delete(flush: true)
            }

            return usuarios
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir lista de usuarios.", e)
        }
    }

    List<Usuario> buscarTodosUsuarios(Map<String, Object> params) {
        try {
            return Usuario.findAll(params)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar todos usuários.", e)
        }
    }

}

