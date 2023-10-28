import Clases.ConexionBaseDeDatos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EliminarProducto")
public class EliminarProductoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtén el ID del producto a eliminar desde el formulario
        int idProductoEliminar = Integer.parseInt(request.getParameter("idEliminar"));

        ConexionBaseDeDatos conexionBaseDeDatos = new ConexionBaseDeDatos();
        Connection connection = conexionBaseDeDatos.conectar();

        if (connection != null) {
            try {
                String consulta = "DELETE FROM producto WHERE id_producto = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(consulta);
                preparedStatement.setInt(1, idProductoEliminar);

                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    response.sendRedirect("index.html"); // Redirige a la página después de eliminar el producto
                } else {
                    response.getWriter().println("No se pudo eliminar el producto.");
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
