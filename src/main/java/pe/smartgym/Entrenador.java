package pe.smartgym;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Entrenador {
    private int id;
    private String nombre;
    private int edad;
    private String correo;
    private String especialidad;

    // Agenda: reserva por fecha y hora exactas
    private Map<LocalDateTime, Reserva> agendaPorFechaHora = new HashMap<>();

    public Entrenador(int id, String nombre, int edad, String correo, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.especialidad = especialidad;
    }

    public boolean estaDisponible(LocalDateTime fechaHora) {
        return !agendaPorFechaHora.containsKey(fechaHora);
    }

    public void agendar(Reserva r, LocalDateTime fechaHora) {
        agendaPorFechaHora.put(fechaHora, r);
    }

    // Getters / Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getCorreo() { return correo; }
    public String getEspecialidad() { return especialidad; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    @Override
    public String toString() {
        return "Entrenador{id=" + id + ", nombre='" + nombre + "', especialidad='" + especialidad + "'}";
    }
}
