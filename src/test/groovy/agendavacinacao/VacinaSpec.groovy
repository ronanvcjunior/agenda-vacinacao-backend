package agendavacinacao

import enums.PeriodicidadeEnum
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class VacinaSpec extends Specification implements DomainUnitTest<Vacina> {

    def "deve criar uma nova vacina com dados válidos"() {
        when:
        Vacina vacina = new Vacina(
                titulo: "Vacina Teste",
                dose: 2,
                periodicidade: PeriodicidadeEnum.MES,
                intervalo: 30
        )

        then:
        vacina.titulo == "Vacina Teste"
        vacina.dose == 2
        vacina.periodicidade == PeriodicidadeEnum.MES
        vacina.intervalo == 30
    }
}
