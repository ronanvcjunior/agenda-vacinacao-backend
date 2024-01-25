package agendavacinacao

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDate

class UsuarioSpec extends Specification implements DomainUnitTest<Usuario> {

    void "deve criar um novo usuário com dados válidos"() {
        when:
        Usuario usuario = new Usuario(
                nome: "John Doe",
                dataNascimento: LocalDate.now(),
                sexo: 'M',
                logradouro: "Street Test",
                numero: 123,
                setor: "Sector Test",
                cidade: "City Test",
                uf: "SP"
        )

        then:
        usuario.nome == "John Doe"
        usuario.dataNascimento != null
        usuario.sexo == 'M'
        usuario.logradouro == "Street Test"
        usuario.numero == 123
        usuario.setor == "Sector Test"
        usuario.cidade == "City Test"
        usuario.uf == "SP"
    }
}
