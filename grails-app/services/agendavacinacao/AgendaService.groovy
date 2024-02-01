package agendavacinacao

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.grails.web.json.JSONArray
import org.hibernate.service.spi.ServiceException

@Transactional
class AgendaService {

    Integer count() {
        return Agenda.count()
    }

    List<Agenda> cadastrarAgenda(Agenda agenda) {
        try {
            agenda.validate()
            if (agenda.hasErrors()) {
                throw new ValidationException("Erro de validação ao cadastrar agenda.", agenda.errors)
            }

            List<Agenda> agendas = []

            for (int i = 0; i < agenda.vacina.dose; i++) {
                def novaAgenda = new Agenda(
                        data: agenda.data,
                        hora: agenda.hora,
                        situacao: agenda.situacao,
                        dataSituacao: agenda.dataSituacao,
                        observacoes: agenda.observacoes,
                        vacina: agenda.vacina,
                        usuario: agenda.usuario
                )
                agendas.add(novaAgenda)
            }

            if (agenda.vacina.dose > 1) {
                agendas = gerarAgendasDatasDosesSeguintes(agendas)
            }
            Agenda.saveAll(agendas)

            return agendas
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao salvar agenda.", e)
        }
    }

    private List<Agenda> gerarAgendasDatasDosesSeguintes(List<Agenda> agendas) {
        agendas.eachWithIndex { Agenda agenda, Integer index ->
            agenda.data = agenda.data.plus((agenda.vacina.intervalo * index), agenda.vacina.periodicidade.temporalUnit)
        }
        return agendas
    }


    Agenda buscarAgenda(Long id) {
        try {
            return Agenda.get(id)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar agenda.", e)
        }
    }

    Agenda atualizarAgenda(Agenda agenda) {
        try {
            agenda.validate()
            if (agenda.hasErrors()) {
                throw new ValidationException("Erro de validação ao atualizar agenda.", agenda.errors)
            }

            agenda.save(flush: true, failOnError: true)

            return agenda
        } catch (ValidationException e) {
            throw new ValidationException(e.message, e.errors)
        } catch (Exception e) {
            throw new ServiceException("Erro ao atualizar agenda.", e)
        }
    }

    Agenda excluirAgenda(Long id) {
        try {
            Agenda agenda = Agenda.get(id)
            if (agenda) {
                agenda.delete(flush: true)
            }

            return agenda
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir agenda.", e)
        }
    }

    List<Agenda> excluirListaAgendas(JSONArray requestBody) {
        List<Agenda> agendas = requestBody.collect { this.buscarAgenda(it["id"] as Long) }
        try {
            agendas.each {
                it.delete(flush: true)
            }

            return agendas
        } catch (Exception e) {
            throw new ServiceException("Erro ao excluir lista de agendas.", e)
        }
    }

    List<Agenda> buscarTodasAgendas(Map<String, Object> params) {
        try {
            return Agenda.findAll(params)
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar todas as agendas.", e)
        }
    }
}
