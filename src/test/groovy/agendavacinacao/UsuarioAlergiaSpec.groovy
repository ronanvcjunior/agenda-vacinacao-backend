package agendavacinacao

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UsuarioAlergiaSpec extends Specification implements DomainUnitTest<UsuarioAlergia> {

    void "deve criar um novo usuário com alergia com dados válidos"() {
        given:
        Usuario usuario = new Usuario(
                nome: "John Doe",
                dataNascimento: Calendar.getInstance(),
                sexo: 'M',
                logradouro: "Street Test",
                numero: 123,
                setor: "Sector Test",
                cidade: "City Test",
                uf: "SP"
        )

        Alergia alergia = new Alergia(
                nome: "Alergia"
        )

        when:
        UsuarioAlergia usuarioAlergia = new UsuarioAlergia(
                usuario: usuario,
                alergia: alergia
        )

        then:
        usuarioAlergia.usuario == usuario
        usuarioAlergia.alergia == alergia
    }
}
