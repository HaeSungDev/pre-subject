package com.nhn.guestbook.service;

import com.nhn.guestbook.dto.GuestbookDTO;
import com.nhn.guestbook.dto.PageRequestDTO;
import com.nhn.guestbook.dto.PageResultDTO;
import com.nhn.guestbook.entity.Guestbook;
import com.nhn.guestbook.entity.QGuestbook;
import com.nhn.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO-------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO guestbookDTO) {
        Optional<Guestbook> result = guestbookRepository.findById(guestbookDTO.getGno());

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle(guestbookDTO.getTitle());
            guestbook.changeContent(guestbookDTO.getContent());

            guestbookRepository.save(guestbook);
        }
    }

    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = this::entityToDto;

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> guestbook = guestbookRepository.findById(gno);

        return guestbook.map(this::entityToDto).orElse(null);
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
