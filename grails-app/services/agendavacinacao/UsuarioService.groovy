package agendavacinacao

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.hibernate.service.spi.ServiceException

@Transactional
class UsuarioService {

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

    List<Usuario> buscarTodosUsuarios(Map<String, Object> params) {
        try {
            List<Usuario> result = Usuario.createCriteria().list(params) {
                maxResults(params.max)
                firstResult(params.offset)
            }

            return result
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar todos usuários.", e)
        }
    }

}

