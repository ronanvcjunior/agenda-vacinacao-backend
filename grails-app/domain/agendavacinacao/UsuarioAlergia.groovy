package agendavacinacao

class UsuarioAlergia {

    static mapping = {
        version false
        id sqlType: 'SERIAL'
    }

    static belongsTo = [usuario: Usuario, alergia: Alergia]
}
