package ar.edu.um.programacion2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdicionalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdicionalDTO.class);
        AdicionalDTO adicionalDTO1 = new AdicionalDTO();
        adicionalDTO1.setId(1L);
        AdicionalDTO adicionalDTO2 = new AdicionalDTO();
        assertThat(adicionalDTO1).isNotEqualTo(adicionalDTO2);
        adicionalDTO2.setId(adicionalDTO1.getId());
        assertThat(adicionalDTO1).isEqualTo(adicionalDTO2);
        adicionalDTO2.setId(2L);
        assertThat(adicionalDTO1).isNotEqualTo(adicionalDTO2);
        adicionalDTO1.setId(null);
        assertThat(adicionalDTO1).isNotEqualTo(adicionalDTO2);
    }
}
