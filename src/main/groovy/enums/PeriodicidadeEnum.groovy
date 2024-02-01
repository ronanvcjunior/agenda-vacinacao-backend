package enums

import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

enum PeriodicidadeEnum {
    DIA(1, "dia", ChronoUnit.DAYS),
    SEMANA(2, "semana", ChronoUnit.WEEKS),
    MES(3, "mês", ChronoUnit.MONTHS),
    ANO(4, "ano", ChronoUnit.YEARS)

    final int codigo
    final String descricao
    final TemporalUnit temporalUnit

    PeriodicidadeEnum(int codigo, String descricao, TemporalUnit temporalUnit) {
        this.codigo = codigo
        this.descricao = descricao
        this.temporalUnit = temporalUnit
    }
}