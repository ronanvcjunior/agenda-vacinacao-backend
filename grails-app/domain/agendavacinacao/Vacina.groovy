package agendavacinacao

import enums.PeriodicidadeEnum
import grails.gorm.annotation.Entity

@Entity
class Vacina {

    String titulo
    Integer dose
    PeriodicidadeEnum periodicidade
    Integer intervalo

    static constraints = {
        titulo nullable: false, blank: false, maxSize: 60
        dose nullable: false, validator: { it > 0 }
        periodicidade nullable: true, inList: PeriodicidadeEnum.values()*.codigo
        intervalo nullable: true, validator: { dose > 1 ? it != null : true }
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
        periodicidade sqlType: 'SMALLINT'
    }
}
