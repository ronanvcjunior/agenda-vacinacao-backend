package agendavacinacao

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class AlergiaSpec extends Specification implements DomainUnitTest<Alergia> {

    void "deve criar uma nova alergia com dados válidos"() {
        when:
        Alergia alergia = new Alergia(
                nome: "Alergia"
        )

        then:
        alergia.nome == "Alergia"
    }
}
