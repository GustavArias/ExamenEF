package pe.com.cibertec.ExamenEF.model.entity;

import org.hibernate.id.IntegralDataTypeHolder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productoId;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "precio", nullable = false)
	private String precio;
	
	@Column(name = "stock", nullable = false)
	private String stock;
	
	@Column(name = "categoria", nullable = false)
	private String categoria;
}
