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
    private Integer nextCursorFollowerCount;
    private String nextCursorBuskerUuid;

    @Builder
    private CursorPage(List<T> content, Boolean hasNext, Integer nextCursorFollowerCount, String nextCursorBuskerUuid) {
        this.content = content;
        this.hasNext = hasNext;
        this.nextCursorFollowerCount = nextCursorFollowerCount;
        this.nextCursorBuskerUuid = nextCursorBuskerUuid;
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
                .build();
    }
}
