package agendavacinacao

import enums.SituacaoEnum
import grails.gorm.annotation.Entity

import java.sql.Time

@Entity
class Agenda {

    Calendar data
    Time hora
    SituacaoEnum situacao
    Calendar dataSituacao
    String observacoes

    static constraints = {
        data nullable: false
        hora nullable: false
        situacao nullable: false, inList: SituacaoEnum.values()*.codigo
        dataSituacao nullable: true, validator: { situacao in [SituacaoEnum.AGENDADO, SituacaoEnum.REALIZADO] ? it != null : true }
        observacoes nullable: true, maxSize: 200
        vacina nullable: false
        usuario nullable: false
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
        situacao sqlType: 'SMALLINT'
        data sqlType: 'DATE'
        dataSituacao sqlType: 'DATE'
    }

    static belongsTo = [vacina: Vacina, usuario: Usuario]
}
