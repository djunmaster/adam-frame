package com.pilot.entity.response;

import com.pilot.entity.base.ResponseEntity;
import com.pilot.entity.base.ResultType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApiModel(value = "ResponseResult", description = "返回对象基类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements ResponseEntity {

    // 返回结果值 0：成功, 1:参数不正确, 2:JSON参数格式错误, 3:数据库操作失败, 4:用户未授权, 5:服务器忙, 6:用户未登录, 7:其他错误, 8:当保存操作时需要进行再次确认
    private int status;

    // 返回结果描述
    private String msg;

    // 返回结果对象
    private List<T> data;

    public ResponseResult(ResultType resultType, List<T> data) {
        this(resultType.getValue(), resultType.getDesc(), sanitizeData(data));
    }

    public ResponseResult(ResultType resultType, String errMsg, List<T> data) {
        this(resultType.getValue(), errMsg, sanitizeData(data));
    }

    // 优化数据处理的静态方法
    private static <T> List<T> sanitizeData(List<T> data) {
        return Optional.ofNullable(data)
                .filter(list -> !(list.size() == 1 && list.get(0) == null))
                .orElse(Collections.emptyList());
    }

    // 静态工厂方法，提供更清晰的对象创建方式
    public static <T> ResponseResult<T> ok() {
        return new ResponseResult<>(ResultType.SUCCESS, Collections.emptyList());
    }

    public static <T> ResponseResult<T> ok(List<T> dataList) {
        return new ResponseResult<>(ResultType.SUCCESS, dataList);
    }

    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult<>(ResultType.OTHER_ERROR, msg, Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseResult)) return false;
        ResponseResult<?> that = (ResponseResult<?>) o;
        return status == that.status &&
                Objects.equals(msg, that.msg) &&
                Objects.equals(data, that.data);
    }

    // 使用 Objects 工具类简化 hashCode 方法
    @Override
    public int hashCode() {
        return Objects.hash(status, msg, data);
    }
}