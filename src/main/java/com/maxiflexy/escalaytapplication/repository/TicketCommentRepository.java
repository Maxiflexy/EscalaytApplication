package com.maxiflexy.escalaytapplication.repository;

import com.maxiflexy.escalaytapplication.entity.model.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketCommentRepository extends JpaRepository <TicketComment, Long> {

    List<TicketComment> findByTicketIdAndParentCommentIsNull(Long ticketId);
    List<TicketComment> findByParentCommentId(Long parentCommentId);

}
