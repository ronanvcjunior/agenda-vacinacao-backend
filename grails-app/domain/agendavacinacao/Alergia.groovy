package agendavacinacao

import grails.gorm.annotation.Entity

@Entity
class Alergia {
    String nome
    static constraints = {
        nome nullable: false, maxSize: 60, unique: true
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
    }

    static hasMany = [usuarioAlergia: UsuarioAlergia]
}
