package com.codesoom.assignment.services;

import com.codesoom.assignment.domains.Task;
import com.codesoom.assignment.domains.TaskDto;
import com.codesoom.assignment.exceptions.TaskInvalidFormatException;
import com.codesoom.assignment.repositories.InMemoryTaskRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TaskUpdateServiceImplTest {

    @InjectMocks
    private TaskUpdateServiceImpl service;

    @Mock
    private InMemoryTaskRepositoryImpl repository;

    private static final Task BEFORE_TASK = new Task(1L, "old title");
    private static final Task AFTER_TASK = BEFORE_TASK.updateTitle("new title");
    private static final String INVALID_TASK_TITLE = "";

    @DisplayName("할 일을 성공적으로 수정한다.")
    @Test
    void updateTask() {
        //given
        given(repository.findById(BEFORE_TASK.getId())).willReturn(BEFORE_TASK);

        //when
        service.updateTaskById(BEFORE_TASK.getId(), new TaskDto("new title"));

        //then
        verify(repository).update(1L, AFTER_TASK);
    }

    @DisplayName("빈 값으로 수정을 시도하면 예외가 발생한다.")
    @Test
    void thrownTaskInvalidException() {
        //given
        final TaskDto invalidTaskDto = new TaskDto(INVALID_TASK_TITLE);

        //when
        //then
        assertThrows(TaskInvalidFormatException.class,
                () -> service.updateTaskById(1L, invalidTaskDto));
    }

}