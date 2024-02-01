package agendavacinacao

import grails.gorm.annotation.Entity

@Entity
class Alergia {
    String nome

    static constraints = {
        nome nullable: false, maxSize: 60, validator: { val, obj, errors ->
            if (val && Alergia.findByNomeIlikeAndIdNotEqual(val, obj.id)) {
                errors.rejectValue('nome', 'alergia.nome.unique', 'Nome já existe no banco de dados.')
                return false
            }
            true
        }
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
    }
}
