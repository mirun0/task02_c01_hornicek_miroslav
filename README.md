# task02_c01_hornicek_miroslav

2. Průběžná úloha: Vyplnění a ořezání n-úhelníkové oblasti

[DONE] Použijte strukturu aplikace navrženou v modulu task2 (viz Oliva-obsah-ukázky a návody). Případně ji upravte.
[DONE] Pro implementaci použijte připravená rozhraní a třídy v package fill.

[DONE] Implementujte algoritmus semínkového vyplnění rastrově zadané oblasti.
[DONE] Myší zadanou hranici oblasti vykreslete na rastrovou plochu plátna barvou odlišnou od barvy vyplnění.
[DONE] Kliknutím vyberte počáteční pixel záplavového algoritmu a plochu vybarvěte.
[DONE] Uvažujte dvě možnosti hraniční podmínky vyplňování. Jednak omezení barvou pozadí a jednak barvou hranice.

[] Implementujte funkci pro kreslení obdelníka zadaného základnou a třetím bodem jehož vzdálenost od základny určuje jeho výšku. Zadání základny obdelníka: stisk plus tažení myši, výška obdelníka: výběr bodu kliknutím v prostoru. Pro uložení vytvořte speciální třídu dědící z třídy Polygon.

[] Implementujte algoritmus ořezání libovolného uzavřeného n-úhelníku konvexním polygonem. Ořezávací polygon může být fixně zadán a musí mít alespoň pět vrcholů. Oba útvary, ořezávaný i ořezávací, jsou zadány polygonem tvořících jejich obvod (geometricky zadaná hranice). Ořezávací polygon uvažujte pouze jako konvexní, případně s kladnou i zápornou orientací vrcholů.
[] Implementujte Scan-line algoritmus vyplnění geometricky zadané plochy n-úhelníku, který je výsledkem ořezání v předchozím kroku.

[DONE] Implementujte funkci na klávesu C pro mazání plátna a všech datových struktur.
[DONE] Bonus: Doplňte možnost editace již zadaného n-úhelníku, změna pozice vrcholu, případně smazání stávajícího či přidání nového vrcholu.
[] Bonus2: Při vyplňovaní rastrově i vektorově zadané hranice implementujte také variantu vyplnění útvaru pravidelně se opakujícím vzorem zadaným předpisem v rozhraní PatternFill.


- fill musi byt po celou dobu createni polygonu zapnuty, aby i po vytvoreni mel atribut pro fill.
