<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Elevator Journeys</title>
</head>
<body bgcolor="grey">
	<div>
		<h2>Completed Elevator Journeys</h2>
	</div>

	<div>
		<div>
			<h1>${errorMessage}</h1>
		</div>
		<h2>All Journeys</h2>
		<div>
			<table border=1 style="text-align: center; padding: 2px;">
				<tr>
					<th>JourneyID</th>
					<th>Time Elapsed</th>
					<th>Max Users Carried</th>
					<th>Started From</th>
					<th>Finished To</th>

				</tr>

				<c:forEach items="${allJourneys}" var="journey">
					<tr>
						<td>${journey.journeyId}</td>
						<td>${journey.timeElapsed}</td>
						<td>${journey.maxUsers}</td>
						<td>${journey.originFloor}</td>
						<td>${journey.destinationFloor}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>


</body>
</html>
