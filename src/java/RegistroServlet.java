import conexion.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {

            Connection con = Conexion.getConexion();

            String sql = "INSERT INTO usuarios(nombre, correo, contraseña) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, password);

            ps.executeUpdate();

            out.println("<html>");
            out.println("<body>");
            out.println("<h2>Usuario registrado correctamente</h2>");
            out.println("<p>Nombre: " + nombre + "</p>");
            out.println("<p>Correo: " + correo + "</p>");
            out.println("</body>");
            out.println("</html>");

            ps.close();
            con.close();

        } catch (SQLException e) {

            out.println("<h2>Error al registrar usuario</h2>");
            out.println("<p>" + e.getMessage() + "</p>");

        }
    }

    @Override
    public String getServletInfo() {
        return "Registro de usuarios";
    }
}