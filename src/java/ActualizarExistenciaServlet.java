import Clases.ConexionBaseDeDatos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ActualizarExistencia")
public class ActualizarExistenciaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtén el ID del producto a actualizar y la nueva existencia desde el formulario
        int idProductoActualizar = Integer.parseInt(request.getParameter("idActualizar"));
        int nuevaExistencia = Integer.parseInt(request.getParameter("nuevaExistencia"));

        ConexionBaseDeDatos conexionBaseDeDatos = new ConexionBaseDeDatos();
        Connection connection = conexionBaseDeDatos.conectar();

        if (connection != null) {
            try {
                String consulta = "UPDATE producto SET existencia = ? WHERE id_producto = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(consulta);
                preparedStatement.setInt(1, nuevaExistencia);
                preparedStatement.setInt(2, idProductoActualizar);

                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    response.sendRedirect("index.html"); // Redirige a la página después de actualizar la existencia
                } else {
                    response.getWriter().println("No se pudo actualizar la existencia del producto.");
                }

                preparedStatement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            response.getWriter().println("No se pudo conectar a la base de datos.");
        }
    }
}
