package ca.neilwhite.hrservice.models;

import lombok.Data;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("authors")
public class Author {
    
   
    
        @Id
        private Long id;
        @Column("first_name")
        private String firstName;
        @Column("last_name")
        private String lastName;
       
    
        public static Author fromRow(Map<String, Object> row) {
            if (row.get("a_id") != null) {
                return Author.builder()
                        .id((Long.parseLong(row.get("a_id").toString())))
                        .firstName((String) row.get("a_firstName"))
                        .lastName((String) row.get("a_lastName"))
                        
                        .build();
            } else {
                return null;
            }
    
        }
    
       
    }
    

