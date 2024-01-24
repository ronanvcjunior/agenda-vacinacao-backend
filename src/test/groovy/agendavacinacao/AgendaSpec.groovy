package agendavacinacao

import enums.PeriodicidadeEnum
import enums.SituacaoEnum
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.sql.Time

class AgendaSpec extends Specification implements DomainUnitTest<Agenda> {

    void "deve criar uma nova agenda com dados válidos"() {
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

        Vacina vacina = new Vacina(
                titulo: "Vacina Teste",
                dose: 2,
                periodicidade: PeriodicidadeEnum.MES,
                intervalo: 30
        )

        when:
        Agenda agenda = new Agenda(
                usuario: usuario,
                vacina: vacina,
                data: Calendar.getInstance(),
                hora: new Time(System.currentTimeMillis()),
                situacao: SituacaoEnum.AGENDADO,
                dataSituacao: null,
                observacoes: "Observações de teste"
        )

        then:
        agenda.usuario == usuario
        agenda.vacina == vacina
        agenda.data
        agenda.hora
        agenda.situacao == SituacaoEnum.AGENDADO
        !agenda.dataSituacao
        agenda.observacoes == "Observações de teste"
    }
}
