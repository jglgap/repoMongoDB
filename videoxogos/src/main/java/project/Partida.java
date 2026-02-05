package project;

public class Partida {

    private String jugador;
    private String nombreJuego;
    private int puntuacion;
    private int duracion;
    private int nivel;


    public Partida() {
    }


    public Partida(String jugador, String nombreJuego, int puntuacion, int duracion, int nivel) {
        this.jugador = jugador;
        this.nombreJuego = nombreJuego;
        this.puntuacion = puntuacion;
        this.duracion = duracion;
        this.nivel = nivel;
    }

    
    public String getJugador() {
        return jugador;
    }
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    public String getNombreJuego() {
        return nombreJuego;
    }
    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }
    public int getPuntuacion() {
        return puntuacion;
    }
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    @Override
    public String toString() {
        return "jugador=" + jugador + ", nombreJuego=" + nombreJuego + ", puntuacion=" + puntuacion
                + ", duracion=" + duracion + ", nivel=" + nivel ;
    }

    
    

}
