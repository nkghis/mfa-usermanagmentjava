package ics.ci.mfausermanagmentjava.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "App_User", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK_EMAIL", columnNames = "email") })
public class AppUser {
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    private String nom;
    private String prenoms;


    private String email;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    private Collection<UserRole> userRoles;

    //Many to many best way to get Chield field, if got one record check order of @Joincolumn name, look Table on database to see good order
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    private Collection<AppRole> roles;

    @Transient
    private String mesroles;

    private String otp;

    private Date otpRequestTime;

    private boolean mfa;

    public AppUser(String email, String encrytedPassword, boolean enabled, boolean mfa) {
        this.email = email;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
        this.mfa = mfa;
    }

    public AppUser(String nom, String prenoms, String email, String encrytedPassword, boolean enabled, boolean mfa) {
       this.nom = nom;
       this.prenoms = prenoms;
        this.email = email;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
        this.mfa = mfa;
    }

    public boolean isOTPRequired() {
        if (this.otp == null) {
            return false;
        }

        long otpRequestedTimeInMillis = this.otpRequestTime.getTime();
        if (otpRequestedTimeInMillis +  OTP_VALID_DURATION < System.currentTimeMillis()) {
            return false; // OTP expired
        }
        return true;
    }

    @Override
    public String toString() {
        return  prenoms + " " + nom ;
    }

}
