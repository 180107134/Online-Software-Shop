package SoftSale;

import java.util.ArrayList;

public class ExpressionHelper
{
	/** \brief Тип условного выражения */
	public enum ExpConditionOpr_t
	{
		/** \brief Меньше */
		LESS,
		/** \brief Равно */
		EQUAL,
		/** \brief Больше */
		GREATER,
		/** \brief Меньше или равно */
		LESS_OR_EQUAL,
		/** \brief Больше или равно */
		GREATER_OR_EQUAL,
	
		LIKE
	}
	
	/** \brief Тип логической операции */
	public enum ExpLogicOpr_t
	{
		/** \brief Логическое НЕ */
		NOT,
		/** \brief Логическое И */
		AND,
		/** \brief Логическое ИЛИ */
		OR
	}
	
	/** \brief Описание условия (элемент WHERE) */
	final class ExpCondition
	{
		/** \brief Наименование поля*/
		String field;
		/** \brief Тип логической операции*/
		ExpConditionOpr_t operator;
		/** \brief Значение условия */
		String value;
		/** \brief Оператор условия */
		ExpLogicOpr_t logicOpr;
	}
	
	/** \brief Описание запроса SELECT*/
	final class ExpDescription_t
	{
		/** \brief Таблица */
		public String table;
		/** \brief Список полей */
		public ArrayList<String> fields;
		/** \brief Список условий */
		public ArrayList<ExpCondition> conditions;
		/** \brief Лимит возвращаемых записей */
		public int limit = 0;
		/** \brief Начальная позиция загружаемых данных */
		public int offset = 0;
	}
	
	private ExpDescription_t _expDescription;
	
	public ExpressionHelper()
	{
		_expDescription = new ExpDescription_t();
		_expDescription.fields = new ArrayList<String>();
		_expDescription.conditions = new ArrayList<ExpCondition>();
	}
	
	/** \brief Сброс параметров запроса */
	public void reset()
	{
		_expDescription.table = "";
		_expDescription.fields.clear();
		_expDescription.conditions.clear();
	}
	
	/**
	 * \brief Задание имени таблицы
	 * \param table Имя таблицы
	 * \note Имя таблицы следует после ключевого слова FROM в запросе
	 */
	public void setTable(String table)
	{
		_expDescription.table = table;
	}
	
	/**
	 * \brief Добавление наименования поля таблицы
	 * \param field Наименование поля таблицы
	 * \note Здесь задаются поля, значения которых будет загружено из БД
	 * \n Список полей следует после ключевого слова SELECT в запросе к БД
	 */
	public void addField(String field)
	{
		for (String f:_expDescription.fields)
		{
			if (f == field)
			{
				return;
			}
		}
		
		_expDescription.fields.add(field);
	}
	
	/**
	 * \brief Добавление условия поиска
	 * \param field Поле таблицы, к которому применяется условие
	 * \param operator Операция, применяемая к полю в таблице
	 * \param value Параметр поиска
	 * \param logiсOpr Операция логического объединения с другими условиями в запросе
	 * \note К первому условию в запросе logicOpr не применяется
	 * \n Список условий следует после ключевого слова WHERE в запросе
	 */
	public void addCondition(String field, ExpConditionOpr_t operator, String value, ExpLogicOpr_t logicOpr)
	{
		ExpCondition condition = new ExpCondition();
		condition.field = field;
		condition.operator = operator;
		condition.value = value;
		condition.logicOpr = logicOpr;
		_expDescription.conditions.add(condition);
	}
	
	/**
	 * \brief Задание лимита возвращаемых из БД записей
	 * \param limit Максимально возможное количество записей возвращаемых в запросе к БД	
	 * \note Ограничение на количество записей следует за ключевым словом LIMIT в запросе к БД
	 */
	public void setLimit(int limit)
	{
		if (limit < 0)
		{
			return;
		}
		
		_expDescription.limit = limit;
	}
	
	/**
	 * 
	 */
	public void setOffset(int offset)
	{
		if (offset < 0)
		{
			return;
		}
		
		_expDescription.offset = offset;
	}
	
	/**
	 * \brief Сборка запроса к базе данных в виде строки
	 * \return Строка в виде SQL-запроса к БД SQLite
	 */
	public String compile()
	{
		String result = new String("SELECT ");
		
		if (_expDescription.table == "")
		{
			return "";
		}
		
		if (_expDescription.fields.size() == 0)
		{
			result += "*";
		}
		else
		{
			Boolean firstTime = true;
			
			for (String field:_expDescription.fields)
			{
				if (!firstTime)
				{
					result += ",";
				}
				
				result += field;
				firstTime = false;
			}
		}
		
		result += " FROM ";
		result += _expDescription.table;
		
		Boolean firstTime = true;
		
		for (ExpCondition condition:_expDescription.conditions)
		{
			
			if (firstTime)
			{
				result += " WHERE ";
			}
			else
			{
				result += " " + condition.logicOpr + " ";
			}
			
			result += condition.field;
			
			switch(condition.operator)
			{
			case LESS:
				result += "<";
				break;
				
			case EQUAL:
				result += "=";
				break;
				
			case GREATER:
				result += ">";
				break;
				
			case LESS_OR_EQUAL:
				result += "<=";
				break;
				
			case GREATER_OR_EQUAL:
				result += ">=";
				break;

			case LIKE:
				result += " LIKE ";
				break;
				
			}
			
			result += condition.value;
			
			firstTime = false;
		}
		
		if (_expDescription.limit > 0)
		{
			result += " LIMIT " + String.valueOf(_expDescription.limit);
		}
		
		if (_expDescription.offset > 0)
		{
			result += " OFFSET " + String.valueOf(_expDescription.offset);
		}
		
		return result;
	}
}



