package dnd.project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.project.domain.bookmark.controller.BookmarkController;
import dnd.project.domain.user.controller.UserController;
import dnd.project.domain.version.controller.VersionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        VersionController.class,
        UserController.class,
        BookmarkController.class
})
@AutoConfigureMockMvc(addFilters = false)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected VersionController versionController;

    @MockBean
    protected UserController userController;

    @MockBean
    protected BookmarkController bookmarkController;
}
