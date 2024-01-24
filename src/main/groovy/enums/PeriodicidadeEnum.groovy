package enums

enum PeriodicidadeEnum {
    DIA(1, "dia"),
    SEMANA(2, "semana"),
    MES(3, "mês"),
    ANO(4, "ano")

    final int codigo
    final String descricao

    PeriodicidadeEnum(int codigo, String descricao) {
        this.codigo = codigo
        this.descricao = descricao
    }
}