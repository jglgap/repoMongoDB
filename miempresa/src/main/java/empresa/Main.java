package empresa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Main {
    public static void main(String[] args) {

        try (MongoProvider mgp = new MongoProvider()) {
            EmpleadoController empleadoController = new EmpleadoController(mgp);
            // empleadoController.crearEmpleado("Juan", 10, 1000, "10/10/1999", null, 0);
            // empleadoController.crearEmpleado("Alicia", 10, 1400, "07/08/2000",
            // "profesor", 0);
            // empleadoController.crearEmpleado("Maria Jesus", 20, 1500, "05/01/2005",
            // "analista", 100);
            // empleadoController.crearEmpleado("Alberto", 20, 1100, "15/11/2001", null, 0);
            // empleadoController.crearEmpleado("Fernando", 30, 1400, "20/11/1999",
            // "analista", 200);
            // empleadoController.buscarEmpleados(new ArrayList<Integer>(List.of(10,20)), 0,
            // null);
            // empleadoController.aumentarSalarios("analista", 100);
            List<Bson> media = Arrays.asList(
                    Aggregates.group(null, Accumulators.avg("media", "$salario")),
                    Aggregates.project(Projections.fields(
                            Projections.excludeId())));
            empleadoController.opcionesAggregate("Media de salarios", media);

     
            for (int i = 10; i < 31; i = i + 10) {
                List<Bson> numAvgMax = Arrays.asList(
                        Aggregates.match(new Document("dep", i)),
                        Aggregates.group(
                                null,
                                Accumulators.sum("totalEmpleados", 1),
                                Accumulators.avg("mediaSalario", "$salario"),
                                Accumulators.max("maxSalario", "$salario")),
                        Aggregates.project(
                                Projections.fields(
                                        Projections.excludeId())));
                empleadoController.opcionesAggregate("Visualiza por departamento el número de empleados, el salario medio y el máximo salario.", numAvgMax);
            }

            // Visualiza el nombre del empleado que tiene el máximo salario.
            
            List<Bson> nameMaxSalary = Arrays.asList(
                    Aggregates.sort(Sorts.descending("salario")),
                    Aggregates.limit(1),
                    Aggregates.project(
                            Projections.fields(
                                    Projections.include("nombre"),
                                    Projections.excludeId())));
            empleadoController.opcionesAggregate("Visualiza el nombre del empleado que tiene el máximo salario.", nameMaxSalary);

        } catch (Exception e) {
            System.out.println("Error conexion " + e.getMessage());
        }

    }
}
