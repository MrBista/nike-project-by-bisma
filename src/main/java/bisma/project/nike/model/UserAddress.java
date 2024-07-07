package bisma.project.nike.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address_users")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "jalan")
    private String jalan;

    @Column(name = "desa")
    private String desa;

    @Column(name = "kecamatan")
    private String kecamatan;

    @Column(name = "kabupaten")
    private String kabupaten;

    @Column(name = "provinsi")
    private String provinsi;

    @Column(name = "negara")
    private String negara;

    @Column(name = "code_pos")
    private String code_pos;

//    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;


    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", jalan='" + jalan + '\'' +
                ", desa='" + desa + '\'' +
                ", kecamatan='" + kecamatan + '\'' +
                ", kabupaten='" + kabupaten + '\'' +
                ", provinsi='" + provinsi + '\'' +
                ", negara='" + negara + '\'' +
                ", code_pos='" + code_pos + '\'' +
                ", user=" + user +
                '}';
    }
}
