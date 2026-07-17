import conexion.Conexion;
import conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        try (Connection con = Conexion.getConexion()) {

            String sql = "INSERT INTO usuarios(nombre, correo, contraseña) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, password);

            ps.executeUpdate();

            ps.close();

            response.sendRedirect("index.jsp");

        } catch (SQLException e) {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Error</title>" +
                "<link rel='stylesheet' href='css/style.css'>" +
                "</head>" +
                "<body>" +
                "<div class='formulario'>" +
                "<h1>Error al registrar</h1>" +
                "<p>No se pudo registrar el usuario.</p>" +
                "<p>" + e.getMessage() + "</p>" +
                "<a href='registro.jsp' class='btn-principal'>Volver</a>" +
                "</div>" +
                "</body>" +
                "</html>"
            );
        }
    }

    @Override
    public String getServletInfo() {
        return "Registro de usuarios";
    }
}