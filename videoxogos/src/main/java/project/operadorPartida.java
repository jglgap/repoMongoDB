package project;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class operadorPartida {

    private MongoProvider mongoProvider;

    public operadorPartida(MongoProvider mongoProvider) {
        this.mongoProvider = mongoProvider;
    }

    public Partida alamacenarpartida(String nombre, String nombreJuego, int puntuacion, int duracion, int nivel) {
        Partida partida = null;
        try {
            partida = new Partida(nombre, nombreJuego, puntuacion, duracion, nivel);
            Document docpartida = new Document("jugador", partida.getJugador())
                    .append("juego", partida.getNombreJuego())
                    .append("puntuacion", partida.getPuntuacion())
                    .append("duracion", partida.getDuracion()).append("nivel", partida.getNivel());

            mongoProvider.partidas().insertOne(docpartida);

        } catch (Exception e) {
            System.out.println("Error creando partida" + "/n" + e.getMessage());

        }
        return partida;
    }

    public void borrarPartida(String jugador){
        Bson filter = Filters.eq("jugador",jugador);

        try {
            Document docPeorPartida = mongoProvider.partidas().find(filter).sort(Sorts.ascending("puntuacion")).first();
            int minPuntuacion = docPeorPartida.getInteger("puntuacion", 0);

            mongoProvider.partidas().deleteOne(
                Filters.and(filter,Filters.eq("puntuacion",minPuntuacion))
            );


        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    public void consultasAggregate(String funcion, List<Bson> pipeline) {
        try {
            AggregateIterable<Document> doc = mongoProvider.partidas().aggregate(pipeline);
            System.out.println(funcion);
            doc.forEach(document -> System.out.println(document));
        } catch (Exception e) {
            System.out.println("Error haciendo consulta" + funcion + "/n" + e.getMessage());
        }

    }

}
