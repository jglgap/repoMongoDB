package empresa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class EmpleadoController {

    private MongoProvider mongoProvider;

    public EmpleadoController(MongoProvider mongoProvider) {
        this.mongoProvider = mongoProvider;
    }

    public Empleados crearEmpleado(String nombre, int dep, int salario, String fechaAlta, String oficio, int comision) {
        Empleados emp = new Empleados(nombre, dep, salario, fechaAlta, oficio, comision);
        Document documentEmp = new Document("nombre", emp.getNombre()).append("dep", emp.getDepartamento())
                .append("salario", emp.getSalario()).append("fechaAlta", emp.getFechaAlta());

        if (emp.getOficio() != null && !emp.getOficio().isBlank()) {
            documentEmp.append("oficio", emp.getOficio());
        }
        if (emp.getComision() != 0) {
            documentEmp.append("comision", emp.getComision());
        }
        mongoProvider.empleados().insertOne(documentEmp);
        return emp;
    }

    public void buscarEmpleados(List<Integer> departamentos, int salario, String oficio) {
        List<Bson> filters = new ArrayList<>();

        // Department filter
        if (departamentos != null && departamentos.size() > 0) {

            filters.add(Filters.in("dep", departamentos));
        }

        // Salary filter
        if (salario > 0) {
            filters.add(Filters.gt("salario", salario));
        }

        // Job filter
        if (oficio != null && !oficio.isBlank()) {
            filters.add(Filters.eq("oficio", oficio));
        }

        if (!filters.isEmpty()) {
            // System.out.println("No filters provided");
            Bson finalFilter = Filters.and(filters);
               mongoProvider.empleados().find(finalFilter).forEach(doc -> System.out.println(doc.toJson()));
        }


        // mongoProvider.empleados()
        // .find(finalFilter).into(new ArrayList<Document>())
        // .forEach(System.out::println);

     
    }

    public void aumentarSalarios(String oficio, int cantidad) {
        Bson filter = Filters.eq("oficio", "analista");
        Bson update = Updates.inc("salario", cantidad);
        mongoProvider.empleados().updateMany(filter, update);
    }

    public void decrementarComisiones(int cantidad) {
        Bson update = Updates.inc("comision", 20);
        mongoProvider.empleados().updateMany(Filters.empty(), update);
    }

    public void opcionesAggregate(String funcion, List<Bson> pipeline) {
        AggregateIterable<Document> doc = mongoProvider.empleados().aggregate(pipeline);
        System.out.println(funcion);
        doc.forEach(document -> System.out.println(document));
    }
}
