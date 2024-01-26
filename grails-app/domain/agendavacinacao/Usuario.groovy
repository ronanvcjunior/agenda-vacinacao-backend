package agendavacinacao

import grails.gorm.annotation.Entity

import java.time.LocalDate

@Entity
class Usuario {

    String nome
    LocalDate dataNascimento
    String sexo
    String logradouro
    BigDecimal numero
    String setor
    String cidade
    String uf

    static hasMany = [alergias: Alergia]

    static constraints = {
        nome nullable: false, blank: false, maxSize: 60
        dataNascimento nullable: false
        sexo nullable: false, blank: false, inList: ['M', 'F']
        logradouro nullable: false, blank: false, maxSize: 60
        numero nullable: true
        setor nullable: false, blank: false, maxSize: 40
        cidade nullable: false, blank: false, maxSize: 40
        uf nullable: false, blank: false, inList: ['AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO']
    }

    static mapping = {
        version false
        id sqlType: 'SERIAL'
        dataNascimento sqlType: 'DATE'
        sexo sqlType: 'CHAR(1)'
        uf sqlType: 'CHAR(2)'
    }
}
