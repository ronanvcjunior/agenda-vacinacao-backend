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
        titulo nullable: false, blank: false, maxSize: 60, validator: { val, obj, errors ->
            if (val && Vacina.findAllByTituloIlikeAndIdNotEqual(val, obj.id)) {
                errors.rejectValue('titulo', 'vacina.titulo.unique', 'Título já existe no banco de dados.')
                return false
            }
            true
        }
        dose nullable: false, min: 1
        periodicidade nullable: true,
                validator: { val, obj, errors -> validateDose('periodicidade', val, obj, errors) }
        intervalo nullable: true,
                validator: { val, obj, errors -> validateDose('intervalo', val, obj, errors) }
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
    }

    def static validateDose(fieldName, val, obj, errors) {
        if (obj.dose > 1 && !val) {
            errors.rejectValue(fieldName, 'vacina.${fieldName}.required', "${fieldName.capitalize()} é obrigatório quando a dose é maior que 1.")
            return false
        }
        else if (obj.dose == 1 && val ) {
            errors.rejectValue(fieldName, 'vacina.${fieldName}.required', "${fieldName.capitalize()} é obrigatório ser nulo quando a dose é igual há 1.")
            return false
        }
        true
    }
}
