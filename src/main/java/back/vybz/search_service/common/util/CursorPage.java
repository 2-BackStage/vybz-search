package back.vybz.search_service.common.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CursorPage<T> {

    private List<T> content;
    private Boolean hasNext;

    // Busker 검색용 커서
    private Integer nextCursorFollowerCount;
    private String nextCursorBuskerUuid;

    // Reels 검색용 커서
    private String nextCursorId;
    private Long nextCursorCreatedAt; // ⬅ 여기 타입을 Long으로 변경함

    @Builder
    private CursorPage(List<T> content,
                       Boolean hasNext,
                       Integer nextCursorFollowerCount,
                       String nextCursorBuskerUuid,
                       String nextCursorId,
                       Long nextCursorCreatedAt) { // ⬅ 생성자도 타입 변경
        this.content = content;
        this.hasNext = hasNext;
        this.nextCursorFollowerCount = nextCursorFollowerCount;
        this.nextCursorBuskerUuid = nextCursorBuskerUuid;
        this.nextCursorId = nextCursorId;
        this.nextCursorCreatedAt = nextCursorCreatedAt;
    }

    public <U> CursorPage<U> map(Function<? super T, ? extends U> mapper) {
        List<U> mappedContent = this.content.stream()
                .map(mapper)
                .collect(Collectors.toList());

        return CursorPage.<U>builder()
                .content(mappedContent)
                .hasNext(this.hasNext)
                .nextCursorFollowerCount(this.nextCursorFollowerCount)
                .nextCursorBuskerUuid(this.nextCursorBuskerUuid)
                .nextCursorId(this.nextCursorId)
                .nextCursorCreatedAt(this.nextCursorCreatedAt) // ⬅ 그대로 유지
                .build();
    }
}
