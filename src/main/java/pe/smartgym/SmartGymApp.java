package pe.smartgym;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class SmartGymApp {

    private Map<Integer, Cliente> clientes = new HashMap<>();
    private Map<Integer, Entrenador> entrenadores = new HashMap<>();
    private List<Reserva> reservas = new ArrayList<>();
    private List<String> logErrores = new ArrayList<>();


    public void registrarCliente(Cliente c) { clientes.put(c.getId(), c); }
    public void registrarEntrenador(Entrenador e) { entrenadores.put(e.getId(), e); }

    
    public Reserva crearReserva(int clienteId, int entrenadorId, LocalDate fecha, LocalTime hora) {
        return crearReserva(clienteId, entrenadorId, fecha, hora, null);
    }

    public Reserva crearReserva(int clienteId, int entrenadorId, LocalDate fecha, LocalTime hora, String comentario) {
        try {
            
            Cliente c = clientes.get(clienteId);
            if (c == null) throw new ClienteNoExisteException("Cliente " + clienteId + " no existe.");

            Entrenador e = entrenadores.get(entrenadorId);
            if (e == null) throw new NoSuchElementException("Entrenador " + entrenadorId + " no existe.");

            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

           
            if (!e.estaDisponible(fechaHora)) {
                throw new ReservaDuplicadaException(
                        "Entrenador " + e.getNombre() + " ya tiene una reserva en " + fechaHora + "."
                );
            }

           
            Reserva r = new Reserva(c, e, fecha, hora, comentario);
            reservas.add(r);
            c.agregarReserva(r);
            e.agendar(r, fechaHora);
            System.out.println("[OK] Reserva creada: " + r);
            return r;

        } catch (RuntimeException ex) {
            String mensaje = "[ERROR] " + ex.getMessage();
            logErrores.add(mensaje);
            System.out.println(mensaje);
            return null; 
        }
    }

    public List<Reserva> listarReservasCliente(int clienteId) {
        Cliente c = clientes.get(clienteId);
        if (c == null) return Collections.emptyList();
        return c.getHistorial();
    }

    public List<String> getLogErrores() { return logErrores; }

    public static void main(String[] args) {
        SmartGymApp app = new SmartGymApp();

        
        app.registrarCliente(new Cliente(1, "Ana", 28, "ana@mail.com"));
        app.registrarCliente(new Cliente(2, "Luis", 35, "luis@mail.com"));
        app.registrarEntrenador(new Entrenador(100, "Marco", 40, "marco@mail.com", "Fuerza"));
        app.registrarEntrenador(new Entrenador(101, "Silvia", 32, "silvia@mail.com", "Funcional"));

      
        System.out.println("---- Escenario 1: Reserva correcta ----");
        app.crearReserva(1, 100, LocalDate.now().plusDays(1), LocalTime.of(9, 0));

      
        System.out.println("---- Escenario 2: Reserva duplicada ----");
        app.crearReserva(2, 100, LocalDate.now().plusDays(1), LocalTime.of(9, 0), "Foco en espalda");

        
        System.out.println("---- Escenario 3: Cliente inexistente ----");
        app.crearReserva(999, 101, LocalDate.now().plusDays(2), LocalTime.of(10, 30));

     
        System.out.println("---- Historial del cliente Ana (id=1) ----");
        for (Reserva r : app.listarReservasCliente(1)) System.out.println(r);

       
        System.out.println("---- Log de errores ----");
        for (String err : app.getLogErrores()) System.out.println(err);

        System.out.println("Total de reservas creadas: " + Reserva.getTotalReservas());
    }
}
