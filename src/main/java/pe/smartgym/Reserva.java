package pe.smartgym;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reserva {
    private static int totalReservas = 0; // contador global
    private final int id;
    private Cliente cliente;
    private Entrenador entrenador;
    private Horario horario;   // clase anidada
    private String comentario; // opcional

    // Constructor base (sin comentario)
    public Reserva(Cliente cliente, Entrenador entrenador, LocalDate fecha, LocalTime hora) {
        this(cliente, entrenador, fecha, hora, null);
    }

    // Constructor sobrecargado (con comentario)
    public Reserva(Cliente cliente, Entrenador entrenador, LocalDate fecha, LocalTime hora, String comentario) {
        this.id = ++totalReservas;
        this.cliente = cliente;
        this.entrenador = entrenador;
        this.horario = new Horario(fecha, hora);
        this.comentario = comentario;
    }

    // Clase anidada
    public static class Horario {
        private LocalDate fecha;
        private LocalTime hora;

        public Horario(LocalDate fecha, LocalTime hora) {
            this.fecha = fecha;
            this.hora = hora;
        }

        public LocalDate getFecha() { return fecha; }
        public LocalTime getHora() { return hora; }
        public LocalDateTime asDateTime() { return LocalDateTime.of(fecha, hora); }

        @Override
        public String toString() { return fecha + " " + hora; }
    }

    // Getters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Entrenador getEntrenador() { return entrenador; }
    public Horario getHorario() { return horario; }
    public String getComentario() { return comentario; }

    public static int getTotalReservas() { return totalReservas; }

    @Override
    public String toString() {
        String base = "Reserva{id=" + id +
                ", cliente=" + cliente.getNombre() +
                ", entrenador=" + entrenador.getNombre() +
                ", horario=" + horario;
        return comentario != null ? base + ", comentario='" + comentario + "'}" : base + "}";
    }
}
