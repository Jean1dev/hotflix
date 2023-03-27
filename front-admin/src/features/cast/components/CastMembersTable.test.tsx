import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { CastMembersTable } from "./CastMembersTable";

const Props = {
  data: {
    itens: [
      {
        id: "123",
        type: "DIRECTOR",
        name: "test",
        deleted_at: null,
        created_at: "2021-03-01T00:00:00.000000Z",
        updated_at: "2021-03-01T00:00:00.000000Z",
      },
    ],
    current_page: 1,
    per_page: 1,
    total: 1,
  },
  perPage: 15,
  isFetching: false,
  rowsPerPage: [15, 25, 50],
  handleOnPageChange: () => {},
  handleFilterChange: () => {},
  handleOnPageSizeChange: () => {},
  handleDelete: () => {},
};

describe("CastMembersTable", () => {
  it("should render castMember talbe correcly", () => {
    const { asFragment } = render(<CastMembersTable {...Props} />, {
      wrapper: BrowserRouter,
    });

    expect(asFragment()).toMatchSnapshot();
  });

  it("should render CastMembersTable with loading", () => {
    const { asFragment } = render(<CastMembersTable {...Props} isFetching />, {
      wrapper: BrowserRouter,
    });

    expect(asFragment()).toMatchSnapshot();
  });

  it("should render CastMembersTable with empty data", () => {
    const { asFragment } = render(
      <CastMembersTable {...Props} data={{ data: [], meta: {} } as any} />,
      { wrapper: BrowserRouter }
    );

    expect(asFragment()).toMatchSnapshot();
  });

});
