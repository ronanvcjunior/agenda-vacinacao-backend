package enums

enum SituacaoEnum {
    AGENDADO(1, "Agendado"),
    REALIZADO(2, "Realizado"),
    CANCELADO(3, "Cancelado")

    final int codigo
    final String descricao

    SituacaoEnum(int codigo, String descricao) {
        this.codigo = codigo
        this.descricao = descricao
    }

    static SituacaoEnum findByCodigo(int codigo) {
        values().find { it.codigo == codigo }
    }
}
