package agendavacinacao

import enums.SituacaoEnum
import grails.gorm.annotation.Entity
import org.springframework.boot.context.properties.bind.DefaultValue

import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

@Entity
class Agenda {

    LocalDate data
    LocalTime hora

    SituacaoEnum situacao = SituacaoEnum.AGENDADO


    LocalDate dataSituacao
    String observacoes

    static constraints = {
        data nullable: false
        hora nullable: false
        situacao nullable: false
        dataSituacao nullable: true, validator: { dataSituacao, obj, errors ->
            if (obj.situacao in [SituacaoEnum.CANCELADO, SituacaoEnum.REALIZADO] && !dataSituacao) {
                errors.rejectValue('dataSituacao', 'agenda.dataSituacao.required', 'A data de situação é obrigatória quando a agenda está cancelada ou realizada.')
                return false
            }
            else if (!(obj.situacao in [SituacaoEnum.CANCELADO, SituacaoEnum.REALIZADO]) && dataSituacao) {
                    errors.rejectValue('dataSituacao', 'agenda.dataSituacao.notAllowed', 'A data de situação deve ser nula quando a agenda não está cancelada ou realizada.')
                    return false
            }
            true
        }
        observacoes nullable: true, maxSize: 200
        vacina nullable: false
        usuario nullable: false
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
        data sqlType: 'DATE'
        dataSituacao sqlType: 'DATE'
    }

    static belongsTo = [vacina: Vacina, usuario: Usuario]
}
