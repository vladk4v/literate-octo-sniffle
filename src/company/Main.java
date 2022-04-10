package company;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

	private static <T> void show(List<T> someList, String message) {
		System.out.println(message);
		for (T t : someList) {
			System.out.println(t);
		}
		System.out.print("\n");
	}

	public static void main(String[] args) {
		List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
		List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
		Collection<Person> persons = new ArrayList<>();
		for (int i = 0; i < 10_000_000; i++) {
			persons.add(new Person(
					names.get(new Random().nextInt(names.size())),
					families.get(new Random().nextInt(families.size())),
					new Random().nextInt(100),
					Sex.values()[new Random().nextInt(Sex.values().length)],
					Education.values()[new Random().nextInt(Education.values().length)])
			);
		}

		//люди младше 18 лет
		long juveniles = persons.stream()
				.filter(person -> person.getAge() < 18)
				.count();
		System.out.printf("Juveniles: %d\n", juveniles);

		//военнообязанные
		List<String> draftees = persons.stream()
				.filter(person -> person.getSex().equals(Sex.MAN))
				.filter(person -> person.getAge() > 18 && person.getAge() < 27)
				.map(Person::getFamily)
				.sorted(Comparator.naturalOrder())
				.collect(Collectors.toList());

		show(draftees, "Draftees: ");

		//работоспособные
		List<Person> ableToWork = persons.stream()
				.filter(person -> person.getEducation().equals(Education.HIGHER))
				.filter(person -> person.getSex().equals(Sex.MAN) ?
						(person.getAge() > 18 && person.getAge() < 65) :
						(person.getAge() > 18 && person.getAge() < 60))
				.sorted(Comparator.comparing(Person::getFamily))
				.collect(Collectors.toList());

		show(ableToWork, "Able to work: ");

	}
}
