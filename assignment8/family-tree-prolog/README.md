# Building a Family Tree in Prolog

This project implements Assignment 8 in Prolog.

It includes:

- family relationship facts
- rules for parent, grandparent, sibling, cousin, and descendant relationships
- sample queries with expected results
- a one-page report draft

## Project Structure

```text
family-tree-prolog/
├── README.md
├── prolog-version/
│   ├── family_tree.pl
│   └── sample_queries.txt
└── report/
    └── assignment8_report.md
```

## How to Run

If SWI-Prolog is installed:

```bash
cd prolog-version
swipl family_tree.pl
```

Then enter queries such as:

```prolog
?- child_of(linda, susan).
?- grandparent_of(robert, emma).
?- sibling_of(linda, james).
?- cousin_of(emma, noah).
?- descendant_of(robert, sophia).
```

Exit with:

```prolog
?- halt.
```

## Submission Notes

- Replace the report placeholder GitHub URL with your real repository link.
- Append screenshots of the Prolog code and sample query output.
- Format the final report in APA 7 style before exporting to PDF or DOCX.
