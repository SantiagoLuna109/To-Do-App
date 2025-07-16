package com.example.v0.demo.mapper;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.model.ToDo;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T01:05:29-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ToDoMapperImpl implements ToDoMapper {

    private final DatatypeFactory datatypeFactory;

    public ToDoMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public ToDoDTO toDto(ToDo entity) {
        if ( entity == null ) {
            return null;
        }

        ToDoDTO toDoDTO = new ToDoDTO();

        toDoDTO.setId( entity.getId() );
        toDoDTO.setText( entity.getText() );
        toDoDTO.setDueDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( entity.getDueDate() ) ) );
        toDoDTO.setDoneFlag( entity.isDoneFlag() );
        toDoDTO.setPriority( entity.getPriority() );

        return toDoDTO;
    }

    @Override
    public ToDo toEntity(ToDoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ToDo toDo = new ToDo();

        toDo.setId( dto.getId() );
        toDo.setText( dto.getText() );
        toDo.setDueDate( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( dto.getDueDate() ) ) );
        toDo.setDoneFlag( dto.isDoneFlag() );
        toDo.setPriority( dto.getPriority() );

        return toDo;
    }

    @Override
    public List<ToDoDTO> toDtoList(List<ToDo> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ToDoDTO> list = new ArrayList<ToDoDTO>( entities.size() );
        for ( ToDo toDo : entities ) {
            list.add( toDto( toDo ) );
        }

        return list;
    }

    @Override
    public List<ToDo> toEntityList(List<ToDoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ToDo> list = new ArrayList<ToDo>( dtos.size() );
        for ( ToDoDTO toDoDTO : dtos ) {
            list.add( toEntity( toDoDTO ) );
        }

        return list;
    }

    @Override
    public void updateEntity(ToDo target, ToDoDTO source) {
        if ( source == null ) {
            return;
        }

        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getText() != null ) {
            target.setText( source.getText() );
        }
        if ( source.getDueDate() != null ) {
            target.setDueDate( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( source.getDueDate() ) ) );
        }
        target.setDoneFlag( source.isDoneFlag() );
        if ( source.getPriority() != null ) {
            target.setPriority( source.getPriority() );
        }
    }

    private XMLGregorianCalendar localDateToXmlGregorianCalendar( LocalDate localDate ) {
        if ( localDate == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }
}
